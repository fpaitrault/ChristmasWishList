package org.fpaitrault.viewmdl;

import java.util.LinkedList;
import java.util.List;

import org.fpaitrault.interfaces.AuthenticationService;
import org.fpaitrault.interfaces.dao.UserDAO;
import org.fpaitrault.interfaces.dao.WishDAO;
import org.fpaitrault.mdl.User;
import org.fpaitrault.mdl.Wish;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class UserGrid {

    @WireVariable
    private AuthenticationService authService = null;
    @WireVariable
    private UserDAO userDAO;
    @WireVariable
    private WishDAO wishDAO;

    private User user = null;
	private boolean currentUserList = false;
	private List<WishStatus> wishStatusList=null;


	@Init
	public void init(@ExecutionArgParam("user") User user) {
		this.user = user;
		this.wishStatusList = new LinkedList<WishStatus>();
		User current = authService.getUserCredential();
		setCurrentUserList(!user.equals(current));
		List<Wish> wishes = this.wishDAO.readAll();

		for(Wish wish : wishes) {
		    if(wish.getDest() == user) {
		        if(getCurrentUserList() || wish.getAuthor().equals(current))
		            wishStatusList.add(new WishStatus(wish, false));
		    }
		}

	}

	public List<WishStatus> getWishList() {
		return wishStatusList;
	}
	
	public List<User> getUsers() {
		return userDAO.readAll();
	}

	@Command
	public void changeEditStatus(@BindingParam("wishStatus") WishStatus ws) {
		ws.setEditStatus(!ws.getEditStatus());
		refreshRowTemplate(ws);
	}
	
	@Command
    @NotifyChange("wishList")
	public void addIdea() {
		Wish wish = new Wish(authService.getUserCredential(),
		        "", "", user, null); //$NON-NLS-1$ //$NON-NLS-2$
		this.wishDAO.create(wish);
		wishStatusList.add(new WishStatus(wish, true));
	}
	
	@Command
	public void confirm(@BindingParam("wishStatus") WishStatus ws) {
		changeEditStatus(ws);
		// Save wish in database
		wishDAO.update(ws.getWish());
	}

    @Command
    @NotifyChange("wishList")
    public void deleteIdea(@BindingParam("wishStatus") final WishStatus ws) {
        EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            public void onEvent(ClickEvent event) throws Exception {
                if(Messagebox.Button.YES.equals(event.getButton())) {
                    wishDAO.delete(ws.getWish());
                    wishStatusList.remove(ws);
                    refreshRowTemplate(ws);
                }
            }
        };
        Messagebox.show(Labels.getLabel("grid.confirm"), //$NON-NLS-1$
                Labels.getLabel("grid.rmidea"), new Messagebox.Button[]{  //$NON-NLS-1$
                Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
    }
	
	private void refreshRowTemplate(WishStatus ws) {
        /*
         * This code is special and notifies ZK that the bean's value
         * has changed as it is used in the template mechanism.
         * This stops the entire Grid's data from being refreshed
         */
        BindUtils.postNotifyChange(null, null, ws, "editStatus"); //$NON-NLS-1$
    }

    public boolean getCurrentUserList() {
        return currentUserList;
    }

    public void setCurrentUserList(boolean currentUserList) {
        this.currentUserList = currentUserList;
    }
}
