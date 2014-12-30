package org.fpaitrault.viewmdl.mgmt;


import java.util.Map;

import org.zkoss.bind.Property;
import org.zkoss.bind.ValidationContext;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.resource.Labels;

public class NewUserValidator extends AbstractValidator {
    
   public void validate(ValidationContext ctx) {
       //all the bean properties
       Map<String,Property> beanProps = ctx.getProperties(ctx.getProperty().getBase());
        
       //first let's check the passwords match
       validatePasswords(ctx, (String)beanProps.get("password").getValue(), (String)ctx.getValidatorArg("retypedPassword")); //$NON-NLS-1$ //$NON-NLS-2$
       validateEmail(ctx, (String)beanProps.get("email").getValue(), (String)ctx.getValidatorArg("retypedEmail")); //$NON-NLS-1$ //$NON-NLS-2$
   }
    
   private void validatePasswords(ValidationContext ctx, String password, String retype) { 
       if(password == null || retype == null || (!password.equals(retype))) {
           this.addInvalidMessage(ctx, "password", Labels.getLabel("admin.userMgmt.passwordmismatch")); //$NON-NLS-1$ //$NON-NLS-2$
       }
   }
    
   private void validateEmail(ValidationContext ctx, String email, String retype) {
       if(email == null || !email.matches(".+@.+\\.[a-z]+") || (!email.equals(retype))) { //$NON-NLS-1$
           this.addInvalidMessage(ctx, "email", Labels.getLabel("admin.userMgmt.emailerror"));             //$NON-NLS-1$ //$NON-NLS-2$
       }
   }

}