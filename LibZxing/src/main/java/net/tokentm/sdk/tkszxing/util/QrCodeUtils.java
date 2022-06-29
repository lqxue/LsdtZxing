package net.tokentm.sdk.tkszxing.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import net.tokentm.sdk.tkszxing.QRCodeEncoder;

/**
 * 二维码条形码的生成工具类
 * @author lqx Email:herolqx@126.com
 */
public class QrCodeUtils {

    //com.google.zxing.oned.OneDimensionalCodeWriter

    /**
     * <pre>
     *     private static BitMatrix renderResult(boolean[] code, int width, int height, int sidesMargin) {
     *     int inputWidth = code.length;
     *     // Add quiet zone on both sides.
     *     int fullWidth = inputWidth + sidesMargin;
     *     int outputWidth = Math.max(width, fullWidth);
     *     int outputHeight = Math.max(1, height);
     *
     *     int multiple = outputWidth / fullWidth;
     *     int leftPadding = (outputWidth - (inputWidth * multiple)) / 2;
     *
     *     BitMatrix output = new BitMatrix(outputWidth, outputHeight);
     *     for (int inputX = 0, outputX = leftPadding; inputX < inputWidth; inputX++, outputX += multiple) {
     *       if (code[inputX]) {
     *         output.setRegion(outputX, 0, multiple, outputHeight);
     *       }
     *     }
     *     return output;
     *   }
     * </pre>
     *
     * <pre>
     * 根据上面源码的判断,最后的BitMatrix的宽度是Math.max(width, fullWidth)
     * 此处要根据条形码内容来区分
     * 1.条形码内容短,比如10位数,那比如指定的宽度是1080px,最后生成的条形码的bitmap就是1080的宽
     * 2.条形码内容过长,比如60位数,那要是指定的宽度是1080px,最后生成的bitmap还是宽为1080px的bitmap,
     *      但是条形码都在中间扎堆,如果此时设置了imageview的缩放也不管用了,因为生成的bitmap已经达到最大的imageview的宽1080px,无法缩放了
     *      这时候呈现出来的就是含有巨大白边的图片效果
     * 总结:当内容过长的时候设置一个很小的宽度,最后反正都要比较,比如设置的宽度是1px,生成的BitMatrix宽度是800px,ok,这个bitmap宽是800px没有巨大白边的bitmap
     * 这时候直接放到一个1080px宽的imageview上设置缩放,直接就填充满了
     * 当内容过短的时候,直接设置自己想要的宽度就可以了,此处直接生成的bitmap是没有白边的直接能用,也不需要imageview的缩放了
     * </pre>
     */
    public static Bitmap createBarCodeSync(String content, int width, int height) {
        if (TextUtils.isEmpty(content)) {
            throw new RuntimeException("内容不能为空");
        }
        Bitmap bitmap = QRCodeEncoder.syncEncodeBarcode(content, width, height, 0);
        if (null == bitmap) {
            throw new RuntimeException("条形码创建失败");
        }
        return bitmap;
    }


    /**
     * 异步执行并显示条形码
     *
     * @param content
     * @param width
     * @param height
     * @return
     */
//    public static void createBarCodeAsync(ImageView showCode, final String content, final int width, final int height) throws Exception {
//        if (showCode == null) {
//            throw new RuntimeException("显示视图不存在");
//        }
//        if (TextUtils.isEmpty(content)) {
//            throw new RuntimeException("内容不能为空");
//        }
//        final int keyContent = 0x7EFFFFFF;
//        showCode.setTag(keyContent, content);
//        final WeakReference<ImageView> viewWeakReference = new WeakReference<>(showCode);
//        TksTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Bitmap bitmap = QRCodeEncoder.syncEncodeBarcode(content, width, height, 0);
//                    if (null == bitmap) {
//                        throw new SDKError("条形码创建失败");
//                    }
//                    TKS.getMainHandler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            ImageView view = (ImageView) viewWeakReference.get();
//                            if (view != null) {
//                                String contentFromTag = (String) view.getTag(keyContent);
//                                if (contentFromTag.equals(content)) {
//                                    view.setImageBitmap(bitmap);
//                                }
//                            }
//                        }
//                    });
//                } catch (InfoException e) {
//                    TksLog.e(e);
//                }
//            }
//        });
//    }

//    /**
//     * 异步执行并显示条形码
//     *
//     * @param content
//     * @param width
//     * @return
//     * @throws InfoException
//     */
//    public static void createQrCodeAsync(ImageView showCode, final String content, final int width) throws InfoException {
//        if (showCode == null) {
//            throw new SDKError("显示视图不存在");
//        }
//        if (TextUtils.isEmpty(content)) {
//            throw new SDKError("内容不能为空");
//        }
//        final int keyContent = 0x7EFFFFFF;
//        showCode.setTag(keyContent, content);
//        final WeakReference<ImageView> viewWeakReference = new WeakReference<>(showCode);
//        TksTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    final Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, width);
//                    if (null == bitmap) {
//                        throw new SDKError("二维码码创建失败");
//                    }
//                    TKS.getMainHandler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            ImageView view = (ImageView) viewWeakReference.get();
//                            if (view != null) {
//                                String contentFromTag = (String) view.getTag(keyContent);
//                                if (contentFromTag.equals(content)) {
//                                    view.setImageBitmap(bitmap);
//                                }
//                            }
//                        }
//                    });
//                } catch (InfoException e) {
//                    TksLog.e(e);
//                }
//            }
//        });
//    }

    /**
     * 创建二维码
     *
     * @param content
     * @param width
     * @return
     */
    public static Bitmap createQrCodeSync(String content, int width) {
        if (TextUtils.isEmpty(content)) {
            throw new RuntimeException("内容不能为空");
        }
        Bitmap bitmap = QRCodeEncoder.syncEncodeQRCode(content, width);
        if (null == bitmap) {
            throw new RuntimeException("二维码创建失败");
        }
        return bitmap;
    }
}
