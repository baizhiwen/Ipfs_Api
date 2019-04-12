package io.ktrade.ipfs.controller;

import io.ktrade.ipfs.IpfsApi;
import io.ktrade.ipfs.common.VisitUserMap;
import io.ktrade.ipfs.exception.APIException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("ipfs")
public class IpfsController {
    private static final Logger logger = LoggerFactory.getLogger(IpfsController.class);

    @Autowired
    private IpfsApi ipfsApi;
    /**
     * 上传文件，返回hash值
     * @return
     */
    @RequestMapping("/addSingleFile")
    public String addSingleFile(MultipartFile file,String visitUser) throws Exception {
        Map<String,String> map = VisitUserMap.userMap;
        if (StringUtils.isBlank(visitUser) || map.get(visitUser) == null ||StringUtils.isBlank(map.get(visitUser))){
            throw new APIException("请求参数或权限错误");
        }
        String addr = ipfsApi.addSingleFile(file);
        return addr;
    }

    /**
     * 上传字符串，返回hash值
     * @param str
     * @return
     * @throws Exception
     */
    @RequestMapping("/addString")
    public String addString(String str,String visitUser) throws Exception {
        Map<String,String> map = VisitUserMap.userMap;
        if (StringUtils.isBlank(visitUser) || map.get(visitUser) == null ||StringUtils.isBlank(map.get(visitUser))){
            throw new APIException("请求参数或权限错误");
        }
        String addr = ipfsApi.addString(str);
        return addr;
    }


}
