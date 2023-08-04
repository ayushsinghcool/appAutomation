package src.utils;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import src.initializers.AppPageInit;

import static java.time.Duration.ofMillis;
import static java.util.Collections.singletonList;

public class ApplicationInteractionActions extends AppPageInit {
    static final String FINGER = "finger";
    static int height;
    static int width;
    static int x;
    static int y;
    static int startX;
    static int startY;
    static int endX;
    static int endY;

    public static void init(double maxY , double minY) {
        Dimension dim = driver.manage().window().getSize();
        height = dim.getHeight();
        width = dim.getWidth();
        x = width - 10;
        y = 0;
        startY = (int) (height * maxY);
        endY = (int) minY;
    }

    public static void slide(WebElement element) {
        WebElement slider = element;
        Point source = slider.getLocation();
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, FINGER);
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0),
                PointerInput.Origin.viewport(), source.x, source.y));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.MIDDLE.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(600)));
        sequence.addAction(finger.createPointerMove(ofMillis(600),
                PointerInput.Origin.viewport(), source.x + 400, source.y));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.MIDDLE.asArg()));
        driver.perform(singletonList(sequence));
    }

    public static void tap() {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, FINGER);
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0),
                PointerInput.Origin.viewport(), x, y));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(100)));
        sequence.addAction(finger.createPointerMove(ofMillis(100),
                PointerInput.Origin.viewport(), x, y));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(singletonList(sequence));
    }

    public static void scrollDown(double maxY , double minY) {
        init(maxY, minY);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, FINGER);
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0),
                PointerInput.Origin.viewport(), x, startY));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(1000)));
        sequence.addAction(finger.createPointerMove(ofMillis(1000),
                PointerInput.Origin.viewport(), x, endY));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(singletonList(sequence));
    }

    public static void scrollUp(double maxY , double minY) {
        init(maxY, minY);
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, FINGER);
        Sequence sequence = new Sequence(finger, 1);
        sequence.addAction(finger.createPointerMove(ofMillis(0),
                PointerInput.Origin.viewport(), x, endY));
        sequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        sequence.addAction(new Pause(finger, ofMillis(1000)));
        sequence.addAction(finger.createPointerMove(ofMillis(1000),
                PointerInput.Origin.viewport(), x, startY));
        sequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(singletonList(sequence));
    }

}

