package Tools;

import java.util.LinkedList;

import EV3.Buttons;
import EV3.Display;

public class SelectMenu implements BrickButtonsListener{

	SelectListener selectListener;
	LinkedList<String> labels = new LinkedList<>();
	int startLine = 0;
	int selected = 0;
	boolean run = true;

	@SuppressWarnings("unused")
	public SelectMenu() {
		Brick center = new Brick(this, Buttons.CENTER);
		Brick down = new Brick(this, Buttons.DOWN);
		Brick up = new Brick(this, Buttons.UP);
	}

	/**
	 * @param selectListener
	 */
	public void addSelectListener(SelectListener selectListener) {
		this.selectListener = selectListener;
	}

	/**
	 * Add a new label.
	 * @param label
	 */
	public void addLabel(String label) {
		labels.add(label);		
	}

	/**
	 * Show the menu.
	 */
	public void show() {
		run = true;
		display();
	}

	/**
	 * Hide the menu.
	 */
	public void hide() {
		run = false;
		Display.resetScreen();
	}

	/**
	 * Displays the menu.
	 */
	private void display() {
		Display.resetScreen();
		if(selected > 4) {
			startLine = (-1)*(selected-4);
		}
		for(int i = 0; i<labels.size(); i++) {
			if (i != selected)
				Display.text("   " + labels.get(i), 0, (i+startLine)*30);
			else
				Display.text(" > " + labels.get(i), 0, (i+startLine)*30);
		}
	}

	/**
	 * @return - The currently selected label.
	 */
	public String getSelectedLabel() {
		return labels.get(selected);
	}

	/**
	 * @return - The currently selected label index.
	 */
	public int getSelectedIndex() {
		return selected;
	}

	public void setLabel(int index, String element) {
		labels.set(index, element);
	}

	@Override
	public void onPress(Buttons button) {
		// Check which button was pressed.
		if(button == Buttons.UP) {
			selected--;
			if(selected < 0)
				selected = 0;
		}
		else if(button == Buttons.DOWN) {
			selected++;
			if(selected == labels.size())
				selected = 0;
		}
		else if(button == Buttons.CENTER) {
			selectListener.onSelect(labels.get(selected));
		}
		if(run)
			display();
	}

}
