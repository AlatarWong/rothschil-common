package xyz.wongs.drunkard.design.adapter;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/8 - 17:21
 * @since 1.0.0
 */
public class StandardAdapter extends Current implements Target{


    @Override
    public int euStandards() {
        return super.nationalStandard()/2;
    }

    @Override
    public int japaneseStandard() {
        return super.nationalStandard()-70;
    }
}
