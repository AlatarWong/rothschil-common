package xyz.wongs.drunkard.alipay.util;

import xyz.wongs.drunkard.alipay.model.hb.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/** 使用google gson作为json序列化反序列化工具
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 *
 * @date 2021/9/23 - 10:16
 * @version 1.0.0
 */
public class GsonFactory {

    private static class GsonHolder {
        private static final Type EXCEPTION_LIST_TYPE = new TypeToken<List<ExceptionInfo>>(){}.getType();
        private static final Type TRADE_INFO_LIST_TYPE = new TypeToken<List<TradeInfo>>(){}.getType();

        private static Gson GSON = new GsonBuilder()
                                .registerTypeAdapter(EXCEPTION_LIST_TYPE, new ExceptionInfoAdapter())
                                .registerTypeAdapter(TRADE_INFO_LIST_TYPE, new TradeInfoAdapter())
                                .registerTypeAdapter(EquipStatus.class, new EquipStatusAdapter())
                                .create();
    }

    public static Gson getGson() {
        return GsonHolder.GSON;
    }
}
