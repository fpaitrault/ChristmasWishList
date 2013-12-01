package org.fpaitrault.viewmdl;

import java.util.LinkedList;
import java.util.List;

import org.fpaitrault.AuthenticationService;
import org.fpaitrault.dao.UserDAO;
import org.fpaitrault.dao.WishDAO;
import org.fpaitrault.mdl.User;
import org.fpaitrault.mdl.Wish;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ExecutionArgParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Messagebox.ClickEvent;

public class UserGrid {

	private User user;
	private boolean currentUserList;
	private List<WishStatus> wishStatusList=new LinkedList<WishStatus>();
    private UserDAO users = new UserDAO();
    private WishDAO wishes = new WishDAO();


	@Init
	public void init(@ExecutionArgParam("user") User user) {
		this.user = user;
		User current = AuthenticationService.instance().getUserCredential();
		setCurrentUserList(!user.equals(current));
		List<Wish> wishes = this.wishes.readAll();

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
		return users.readAll();
	}

	@Command
	public void changeEditStatus(@BindingParam("wishStatus") WishStatus ws) {
		ws.setEditStatus(!ws.getEditStatus());
		refreshRowTemplate(ws);
	}
	
	@Command
    @NotifyChange("wishList")
	public void addIdea() {
		Wish wish = new Wish(AuthenticationService.instance().getUserCredential(),
		        "", "", user, null);
		this.wishes.create(wish);
		wishStatusList.add(new WishStatus(wish, true));
	}
	
	@Command
	public void confirm(@BindingParam("wishStatus") WishStatus ws) {
		changeEditStatus(ws);
		// Save wish in database
		wishes.update(ws.getWish());
	}

    @Command
    @NotifyChange("wishList")
    public void deleteIdea(@BindingParam("wishStatus") final WishStatus ws) {
        EventListener<ClickEvent> clickListener = new EventListener<Messagebox.ClickEvent>() {
            public void onEvent(ClickEvent event) throws Exception {
                if(Messagebox.Button.YES.equals(event.getButton())) {
                    wishes.delete(ws.getWish());
                    wishStatusList.remove(ws);
                    refreshRowTemplate(ws);
                }
            }
        };
        Messagebox.show("Etes-vous sûr ?", "Supprimer l'idée", new Messagebox.Button[]{
                Messagebox.Button.YES, Messagebox.Button.NO }, Messagebox.QUESTION, clickListener);
    }
	
	private void refreshRowTemplate(WishStatus ws) {
        /*
         * This code is special and notifies ZK that the bean's value
         * has changed as it is used in the template mechanism.
         * This stops the entire Grid's data from being refreshed
         */
        BindUtils.postNotifyChange(null, null, ws, "editStatus");
    }

    public boolean getCurrentUserList() {
        return currentUserList;
    }

    public void setCurrentUserList(boolean currentUserList) {
        this.currentUserList = currentUserList;
    }
}
