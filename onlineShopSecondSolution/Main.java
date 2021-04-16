package onlineShopSecondSolution;

import onlineShopSecondSolution.core.EngineImpl;
import onlineShopSecondSolution.core.interfaces.Engine;

public class Main {
    public static void main(String[] args) {
        Engine engine = new EngineImpl();
        engine.run();
    }
}
