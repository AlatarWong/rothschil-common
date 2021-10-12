package xyz.wongs.drunkard.alipay.model.hb;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.apache.commons.lang.StringUtils;
import xyz.wongs.drunkard.common.utils.Utils;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 *
 * @date 2018/4/23 - 10:20
 * @since 1.0.0
 */
public class TradeInfoAdapter implements JsonSerializer<List<TradeInfo>> {
    @Override
    public JsonElement serialize(List<TradeInfo> tradeInfoList, Type type, JsonSerializationContext jsonSerializationContext) {
        if (Utils.isListEmpty(tradeInfoList)) {
            return null;
        }

        TradeInfo tradeInfo = tradeInfoList.get(0);
        if (tradeInfo instanceof PosTradeInfo) {
            return new JsonPrimitive(StringUtils.join(tradeInfoList, ""));
        }

        return jsonSerializationContext.serialize(tradeInfoList);
    }
}
