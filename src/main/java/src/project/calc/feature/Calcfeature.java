package src.project.calc.feature;

import src.project.calc.pageObject.CommonPageObject;

public class Calcfeature {
    CommonPageObject commonPageObject = new CommonPageObject();

    public void performAction(String str){
        commonPageObject.clickOne();
    }

}
