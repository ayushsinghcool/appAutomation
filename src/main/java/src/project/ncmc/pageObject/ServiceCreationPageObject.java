package src.project.ncmc.pageObject;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;
import src.initializers.AppPageInit;

public class ServiceCreationPageObject extends AppPageInit {

    public ServiceCreationPageObject(){
        super();
    }

    @AndroidFindBy(id = "tvServiceCreation")
    private WebElement serviceCreation;
}
