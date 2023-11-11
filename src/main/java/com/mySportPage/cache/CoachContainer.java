package com.mySportPage.cache;

import com.mySportPage.model.Coach;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Scope("singleton")
@Profile({"production", "test"})
public class CoachContainer {

    private static Map<Integer, List<Coach>> coaches = new HashMap<>();

    public static Map<Integer, List<Coach>> getCoaches() {
        return coaches;
    }

    public synchronized static void setCoaches(Map<Integer, List<Coach>> coaches) {
        CoachContainer.coaches = coaches;
    }
}
