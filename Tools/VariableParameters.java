package Tools;

import java.util.HashMap;
import java.util.LinkedList;

public class VariableParameters implements SelectListener{

	/////////////////////////////////////////////
	///////////// NOT USED //////////////////////
	/////////////////////////////////////////////

	HashMap<String, Double> parametersMap = new HashMap<>();
	LinkedList<Double> parameters = new LinkedList<>();
	double change = 0.1;
	SelectMenu view = new SelectMenu();

	public VariableParameters() {
		view.addSelectListener(this);
	}

	public void addParameter(String name, double defaultValue) {
		view.addLabel(name + ": " + defaultValue);
		parameters.add(defaultValue);
		parametersMap.put(name, defaultValue);
	}

	@SuppressWarnings("unused")
	private void increaseVariable(int index) {
		parameters.set(index, parameters.get(index) + change);
	}

	@SuppressWarnings("unused")
	private void decreaseVariable(int index) {
		parameters.set(index, parameters.get(index) + change);
	}

	@Override
	public void onSelect(String selectedLabel) {
		while(true) {

		}
	}


}
