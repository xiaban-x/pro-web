package com.xiaban.qqzone.service;

import com.xiaban.qqzone.dao.HostReplyDAO;
import com.xiaban.qqzone.pojo.HostReply;

public interface HostReplyService {
    HostReply getHostReplyByReplyId(Integer replyId);
    void delHostReply(Integer id);
}
