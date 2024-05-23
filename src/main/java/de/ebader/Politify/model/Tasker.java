package de.ebader.Politify.model;

import de.ebader.Politify.controller.dao.JsonParserAPI;

public class Tasker {

    private int taskerID;
    @SuppressWarnings("unused")
    private String taskerName;
    private static JsonParserAPI jsonParser = new JsonParserAPI();

    public Tasker(Integer taskerID) {
        this.taskerID = taskerID;
        this.taskerName = jsonParser.parseTaskerName(taskerID);
    }

    public int getTaskerID() {
        return taskerID;
    }

    public String getTaskerName() {
        return jsonParser.parseTaskerName(taskerID);
    }

}
