package com.poscodx.mysite.web.mvc.board;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class BoardActionFactory implements ActionFactory {
	@Override
	public Action getAction(String actionName) {
		
		Action action = null;
		
		if("writeform".equals(actionName)) {
			action = new WriteFromAction();
		} else if("write".equals(actionName)) {
			action = new WriteAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("modifyform".equals(actionName)) {
			action = new ModifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("viewform".equals(actionName)) {
			action = new ViewFromAction();
		} else if("view".equals(actionName)) {
			action = new ViewAction();
		} else {
			action = new ListAction();
		}
		return action;
	}
}