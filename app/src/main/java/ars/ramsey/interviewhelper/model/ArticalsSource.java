package ars.ramsey.interviewhelper.model;

import io.reactivex.Observable;

/**
 * Created by Ramsey on 2017/5/9.
 */

public interface ArticalsSource {

    Observable getTasks(String id,int limit,int offset);

}
