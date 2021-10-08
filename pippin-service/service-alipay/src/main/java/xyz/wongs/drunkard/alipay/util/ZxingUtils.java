package xyz.wongs.drunkard.alipay.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import xyz.wongs.drunkard.base.message.enums.Status;
import xyz.wongs.drunkard.base.message.exception.DrunkardException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;


/** 使用了zxing库进行二维码的生成
 * @author <a href="https://github.com/rothschil">Sam</a>
 * 
 * @date 2021/9/23 - 10:16
 * @since 1.0.0
 */
public class ZxingUtils {
	private static Log log = LogFactory.getLog(ZxingUtils.class);

    private static final int BLACK = 0xFF000000;
    private static final int WHITE = 0xFFFFFFFF;

    private static BufferedImage toBufferedImage(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
            }
        }
        return image;
    }

    private static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
        BufferedImage image = toBufferedImage(matrix);
        if (!ImageIO.write(image, format, file)) {
            throw new IOException("Could not write an image of format " + format + " to " + file);
        }
    }


    /** 将内容contents生成长宽均为width的图片，图片路径由imgPath指定
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/26-10:36
     * @param contents  图片内容
     * @param width 图片宽度
     * @param imgPath   图片保存路径
     * @return File
     **/
    public static File getQRCodeImge(String contents, int width, String imgPath) {
        return getQRCodeImge(contents, width, width, imgPath);
    }


    /** 将内容contents生成长为width，宽为width的图片，图片路径由imgPath指定
     * @author <a href="https://github.com/rothschil">Sam</a>
     * @date 2021/9/26-10:37
     * @param contents 内容
     * @param width 宽度
     * @param height    高度
     * @param imgPath   图片保存路径
     * @return File
     **/
	public static File getQRCodeImge(String contents, int width, int height, String imgPath) {
		try {
            Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
            hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
            hints.put(EncodeHintType.CHARACTER_SET, "UTF8");

			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);

            File imageFile = new File(imgPath);
			writeToFile(bitMatrix, "png", imageFile);
            return imageFile;
		} catch (Exception e) {
			log.error("create QR code error!", e);
            throw new DrunkardException(Status.QR_CODE_ERR);
		}
	}
}
