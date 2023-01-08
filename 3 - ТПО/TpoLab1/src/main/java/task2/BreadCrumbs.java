package task2;

public class BreadCrumbs {
    private String trace = "";

    public String getTrace() {
        return trace;
    }

    public void appendCrumb(Integer crumb){
        trace += 'f'+crumb.toString();
    }
}
