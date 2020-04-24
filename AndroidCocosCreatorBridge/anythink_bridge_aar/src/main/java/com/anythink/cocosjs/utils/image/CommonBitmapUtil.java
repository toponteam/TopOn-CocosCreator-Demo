
package com.anythink.cocosjs.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;

import java.io.File;
import java.io.FileDescriptor;

/**
 * Bitmap创建类
 *
 * @author chenys
 */
public class CommonBitmapUtil {

//    @SuppressLint("NewApi")
//	public static Drawable convertBitmap2Drawable(Resources res, Bitmap bitmap) {
//        BitmapDrawable bd = new BitmapDrawable(res, bitmap);
//        // 因为BtimapDrawable是Drawable的子类，最终直接使用bd对象即可。
//        return (Drawable) bd;
//    }

    public static Bitmap decodeFile(String pathName) {
        Bitmap bitmap = null;
        if (isFileExist(pathName)) {
            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(pathName, opts);
            opts.inJustDecodeBounds = false;
            opts.inPurgeable = true;
            opts.inInputShareable = true;
            opts.inDither = true;

            try {
                bitmap = BitmapFactory.decodeFile(pathName, opts);
            } catch (OutOfMemoryError e) {
                System.gc();
                try {
                    opts.inPreferredConfig = Config.RGB_565;
                    bitmap = BitmapFactory.decodeFile(pathName, opts);
                    opts.inPreferredConfig = Config.ARGB_8888;
                } catch (OutOfMemoryError e1) {
                }
            } catch (Exception e2) {
            }
        } else {
            return null;
        }
        return bitmap;
    }


    /**
     * 设置图片圆角
     *
     * @param bitmap
     * @param pixels 圆角度
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
    }

    /**
     * Calculate an inSampleSize for use in a {@link Options} object
     * when decoding bitmaps using the decode* methods from {@link BitmapFactory}.
     * This implementation calculates the closest inSampleSize that is a power of 2 and will result
     * in the final decoded bitmap having a width and height equal to or larger than the requested
     * width and height.
     *
     * @param options   An options object with out* params already populated (run through a decode*
     *                  method with inJustDecodeBounds==true
     * @param reqWidth  The requested width of the resulting bitmap
     * @param reqHeight The requested height of the resulting bitmap
     * @return The value to be used for inSampleSize
     */
    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        // BEGIN_INCLUDE (calculate_sample_size)
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (reqWidth <= 0 && reqHeight <= 0) {
            return inSampleSize;
        }
        if (reqWidth > 0 && reqHeight == 0) {
            //对于只需要宽度的图片进行按照宽度缩放
        }
        if (height > reqHeight || width > reqWidth) {
            if (reqWidth > 0 && reqHeight == 0) {
                //对于只需要宽度的图片进行按照宽度缩放
                reqHeight = height * reqWidth / width;
            }
            if (reqHeight > 0 && reqWidth == 0) {
                //对于只需要宽度的图片进行按照宽度缩放
                reqWidth = width * reqHeight / height;
            }
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }

            // This offers some additional logic in case the image has a strange
            // aspect ratio. For example, a panorama may have a much larger
            // width than height. In these cases the total pixels might still
            // end up being too large to fit comfortably in memory, so we should
            // be more aggressive with sample down the image (=larger inSampleSize).

            long totalPixels = width * height / (inSampleSize * inSampleSize);

            // Anything more than 2x the requested pixels we'll sample down further
            final long totalReqPixelsCap = reqWidth * reqHeight * 4;

            while (totalPixels > totalReqPixelsCap && ((totalPixels / 4) > totalReqPixelsCap)) {
                inSampleSize *= 2;
                totalPixels /= 4;
            }
        }
        return inSampleSize;
        // END_INCLUDE (calculate_sample_size)
    }

    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath) || TextUtils.isEmpty(filePath.trim())) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

//    public static Bitmap blurBitmap(Context context, Bitmap bitmap) {
//        try {
//            //用需要创建高斯模糊bitmap创建一个空的bitmap
//            Bitmap outBitmap = Bitmap.createBitmap(bitmap.getWidth()/3, bitmap.getHeight()/3, Config.ARGB_8888);
//            // 初始化Renderscript，该类提供了RenderScript context，创建其他RS类之前必须先创建这个类，其控制RenderScript的初始化，资源管理及释放
//            RenderScript rs = RenderScript.create(context);
//            // 创建高斯模糊对象
//            ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
//            // 创建Allocations，此类是将数据传递给RenderScript内核的主要方 法，并制定一个后备类型存储给定类型
//            Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
//            Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
//            //设定模糊度(注：Radius最大只能设置25.f)
//            blurScript.setRadius(25.f);
//            // Perform the Renderscript
//            blurScript.setInput(allIn);
//            blurScript.forEach(allOut);
//            // Copy the final bitmap created by the out Allocation to the outBitmap
//            allOut.copyTo(outBitmap);
//            // recycle the original bitmap
//            // bitmap.recycle();
//            // After finishing everything, we destroy the Renderscript.
//
//            Canvas c = new Canvas(outBitmap);
//            c.drawColor(0x33000000); //如果不设置颜色，默认是透明背景
//
//            rs.destroy();
//            return outBitmap;
//        } catch (Exception e) {
//
//        }
//
//        return null;
//
//    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
