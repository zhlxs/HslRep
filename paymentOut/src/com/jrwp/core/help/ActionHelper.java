package com.jrwp.core.help;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ActionHelper {

	private List<ActionHelper.Action> items;
	@JsonIgnore
	private List<com.jrwp.core.entity.Action> actions;

	public ActionHelper(List<com.jrwp.core.entity.Action> actions) {
		this.actions = actions;
		items = getActionJson(actions, "", 1);
	}

	private List<Action> getActionJson(
			List<com.jrwp.core.entity.Action> actions, String parentAction,
			int flag) {
		List<Action> list = new ArrayList<ActionHelper.Action>();
		if (actions != null) {
			for (com.jrwp.core.entity.Action action : actions) {
				Action a = new Action();
				a.setDisplay(action.getName());
				if (parentAction.equals("")) {
					a.setControllerAction(action.getUrl().get(0));
				} else {
					a.setControllerAction(parentAction + "|"
							+ action.getUrl().get(0));
				}
				a.setTypeOf(flag);
				List<com.jrwp.core.entity.Action> actionsChildren = action
						.getActions();
				a.setChildren(getActionJson(actionsChildren, action.getUrl()
						.get(0), flag + 1));
				a.setArea(action.getArea());
				list.add(a);
			}
		}
		if (list.size() == 0) {
			list = null;
		}
		return list;
	}

	public List<ActionHelper.Action> getItems() {
		return items;
	}

	public void setItems(List<ActionHelper.Action> items) {
		this.items = items;
	}

	public List<com.jrwp.core.entity.Action> getActions() {
		return actions;
	}

	public void setActions(List<com.jrwp.core.entity.Action> actions) {
		this.actions = actions;
	}

	public class Action {
		private String display;
		private String area;
		private String controllerAction;
		private Integer typeOf;
		private List<Action> children;

		public String getDisplay() {
			return display;
		}

		public void setDisplay(String display) {
			this.display = display;
		}

		public String getArea() {
			return area;
		}

		public void setArea(String area) {
			this.area = area;
		}

		public String getControllerAction() {
			return controllerAction;
		}

		public void setControllerAction(String controllerAction) {
			this.controllerAction = controllerAction;
		}

		public Integer getTypeOf() {
			return typeOf;
		}

		public void setTypeOf(Integer typeOf) {
			this.typeOf = typeOf;
		}

		public List<Action> getChildren() {
			return children;
		}

		public void setChildren(List<Action> children) {
			this.children = children;
		}

	}
}
