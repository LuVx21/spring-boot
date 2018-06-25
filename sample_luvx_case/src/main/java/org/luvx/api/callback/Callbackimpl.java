package org.luvx.api.callback;


public class Callbackimpl implements Callback {

    @Override
    public void fix(String question, Caller caller) {
        System.out.println("this will callback " + question);
        fixfix(question);
        caller.called();
    }

    public void fixfix(String problem){
        System.out.println("fix!fix!fix!fixed question");
    }

}
