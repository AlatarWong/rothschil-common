package io.github.rothschil.common.exception;

import java.io.PrintWriter;

public class NullServiceException extends Exception {

    /**
     * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
     */
    private static final long serialVersionUID = 1L;

	public NullServiceException(){

    }

	public NullServiceException(String msg){
        super(msg);
    }

    @Override
    public void printStackTrace() {
        // TODO Auto-generated method stub
        super.printStackTrace();
    }

    @Override
    public void printStackTrace(PrintWriter s) {
        // TODO Auto-generated method stub
        super.printStackTrace(s);
    }



}
