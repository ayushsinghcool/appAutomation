package src.globalConstant;

public class BooleanController {
    public static boolean isTIDActivationRequired = false;
    public static boolean isFirstTimeOnboarding = true;
    public static void setTIDActivationTrue(){
        isTIDActivationRequired = true;
    }
    public static void setFirstTimeOnboardingFalse(){
        isFirstTimeOnboarding = false;
    }
}
