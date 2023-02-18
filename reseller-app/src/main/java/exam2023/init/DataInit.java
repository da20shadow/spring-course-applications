package exam2023.init;

import exam2023.services.ConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInit implements CommandLineRunner {


    private final ConditionService conditionService;

    @Autowired
    public DataInit(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.conditionService.insertConditions();
    }
}
