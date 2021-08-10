package com.buka.amanah.model.branch_list;

public class DataBranch {
    private String id;

    private String branch;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getBranch ()
    {
        return branch;
    }

    public void setBranch (String branch)
    {
        this.branch = branch;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", branch = "+branch+"]";
    }
}
