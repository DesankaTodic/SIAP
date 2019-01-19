/*
 *  RapidMiner
 *
 *  Copyright (C) 2001-2014 by RapidMiner and the contributors
 *
 *  Complete list of developers available at our web site:
 *
 *       http://rapidminer.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package com.rapidminer.gui.tour;

import java.awt.Component;
import java.awt.Window;

import com.rapidminer.ProcessSetupListener;
import com.rapidminer.gui.RapidMinerGUI;
import com.rapidminer.gui.properties.OperatorPropertyPanel;
import com.rapidminer.gui.tools.components.BubbleToButton;
import com.rapidminer.gui.tools.components.BubbleToDockable;
import com.rapidminer.gui.tools.components.BubbleToOperator;
import com.rapidminer.gui.tools.components.BubbleWindow;
import com.rapidminer.gui.tools.components.BubbleWindow.AlignedSide;
import com.rapidminer.operator.ExecutionUnit;
import com.rapidminer.operator.Operator;

/**
 * This Subclass of {@link Step} will open a {@link BubbleWindow} which closes if a {@link Operator} of the given Class was renamed to the given String.
 * 
 * @author Philipp Kersting and Thilo Kamradt
 *
 */

public class RenameOperatorStep extends Step {

	private AlignedSide alignment;
	private Window owner = RapidMinerGUI.getMainFrame();
	private BubbleTo element;
	private String i18nKey;
	private String targetName;
	private Class<? extends Operator> operatorClass;
	private String buttonKey = "rename_in_processrenderer";
	private ProcessSetupListener listener = null;
	private String dockableKey = OperatorPropertyPanel.PROPERTY_EDITOR_DOCK_KEY;
	
	/**
	 * should be used to attach the Operator which should be renamed, the operator-property-panel or the rename button in the property-panel of the operator
	 * @param preferredAlignment offer for alignment but the Class will calculate by itself whether the position is usable.
	 * @param i18nKey of the message which will be shown in the {@link BubbleWindow}.
	 * @param operatorClass Class or Superclass of the {@link Operator} which should be renamed.
	 * @param targetName the Name the {@link Operator} should have after this Step.
	 */
	public RenameOperatorStep(BubbleTo element, AlignedSide preferredAlignment, String i18nKey, Class<? extends Operator> operatorClass, String targetName) {
		super();
		this.alignment = preferredAlignment;
		this.i18nKey = i18nKey;
		this.targetName = targetName;
		this.operatorClass = operatorClass;
		this.element = element;
	}
	
	/**
	 * @param preferredAlignment offer for alignment but the Class will calculate by itself whether the position is usable.
	 * @param owner the {@link Window} on which the {@link BubbleWindow} should be shown.
	 * @param i18nKey of the message which will be shown in the {@link BubbleWindow}.
	 * @param operatorClass Class or Superclass of the {@link Operator} which should be renamed.
	 * @param targetName the Name the {@link Operator} should have after this Step.
	 * @param attachToKey i18nkey of the {@link Component} to which the {@link BubbleWindow} should point to.
	 */
	public RenameOperatorStep(BubbleTo element, AlignedSide preferredAlignment, String i18nKey, Class<? extends Operator> operatorClass, String targetName, String elementKey, Window owner) {
		super();
		this.alignment = preferredAlignment;
		this.i18nKey = i18nKey;
		this.targetName = targetName;
		this.operatorClass = operatorClass;
		this.element = element;
		this.owner = owner;
		if(element == BubbleTo.DOCKABLE) {
			this.buttonKey = elementKey;
		} else if(element == BubbleTo.BUTTON) {
			this.dockableKey = elementKey;
		}
	}

	@Override
	boolean createBubble() {
		switch(element) {
			case BUTTON:
				bubble = new BubbleToButton(owner, dockableKey, alignment, i18nKey, buttonKey, false, new Object[] {});
				break;
			case DOCKABLE:
				bubble = new BubbleToDockable(owner, alignment, i18nKey, dockableKey, new Object[] {});
				break;
			case OPERATOR:
				bubble = new BubbleToOperator(owner, alignment, i18nKey, operatorClass, new Object[] {});
		}
		listener = new ProcessSetupListener() {

			@Override
			public void operatorRemoved(Operator operator, int oldIndex, int oldIndexAmongEnabled) {
				// don't care

			}

			@Override
			public void operatorChanged(Operator operator) {
				if (RenameOperatorStep.this.operatorClass.isInstance(operator) && operator.getName().equals(targetName)) {
					bubble.triggerFire();
					RapidMinerGUI.getMainFrame().getProcess().removeProcessSetupListener(this);
				}
			}

			@Override
			public void operatorAdded(Operator operator) {
				// don't care

			}

			@Override
			public void executionOrderChanged(ExecutionUnit unit) {
				// don't care

			}
		};
		RapidMinerGUI.getMainFrame().getProcess().addProcessSetupListener(listener);
		return true;
	}

	@Override
	protected void stepCanceled() {
		if(listener != null)
			RapidMinerGUI.getMainFrame().getProcess().removeProcessSetupListener(listener);
	}

	@Override
	public Step[] getPreconditions() {
		return new Step[] {new PerspectivesStep(1), new NotOnScreenStep(dockableKey)};
	}
}
