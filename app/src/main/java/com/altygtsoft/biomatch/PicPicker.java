package com.altygtsoft.biomatch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfFloat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.core.Scalar;
import org.opencv.features2d.DMatch;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.features2d.Features2d;
import org.opencv.imgproc.Imgproc;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PicPicker extends ActionBarActivity {

    public ImageView imgSelected1;
    public Button btnSelect1;
    public ImageView imgSelected2;
    public Button btnSelect2;
    public Button btnAnaliz;
    public int imgNO;

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {

                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_picker);

        startCast();

    }
    @Override
    public void onResume() {
        super.onResume();

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
    }

    private void startCast() {

        imgSelected1 = (ImageView)findViewById(R.id.imageSelected1);
        imgSelected2 = (ImageView)findViewById(R.id.imageSelected2);

        btnSelect1 = (Button)findViewById(R.id.btnSelect1);
        btnSelect2 = (Button)findViewById(R.id.btnSelect2);

        btnAnaliz = (Button)findViewById(R.id.btnStart);

        btnSelect1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
                imgNO = 1;

            }
        });

        btnSelect2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, 1);
                imgNO = 2;
            }
        });

        btnAnaliz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imgSelected1.getDrawable() == null || imgSelected2 == null)
                {

                    Toast.makeText(getApplicationContext(), "Fotoğraflar Seçilmeli !", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Bitmap bmpImg1 = Bitmap.createScaledBitmap(((BitmapDrawable) imgSelected1.getDrawable()).getBitmap(), 200, 200, true);
                    Bitmap bmpImg2 = Bitmap.createScaledBitmap(((BitmapDrawable)imgSelected2.getDrawable()).getBitmap(),200,200,true);

                    Mat img1 = new Mat();
                    Utils.bitmapToMat(bmpImg1, img1);
                    Mat img2 = new Mat();
                    Utils.bitmapToMat(bmpImg2, img2);

                    Imgproc.cvtColor(img1, img1, Imgproc.COLOR_RGBA2GRAY);
                    Imgproc.cvtColor(img2, img2, Imgproc.COLOR_RGBA2GRAY);

                    img1.convertTo(img1, CvType.CV_32F);//Maske Çıkarıyor
                    img2.convertTo(img2, CvType.CV_32F);

                    Mat hist1 = new Mat();
                    Mat hist2 = new Mat();

                    MatOfInt histSize = new MatOfInt(180);
                    MatOfInt channels = new MatOfInt(0);
                    ArrayList<Mat> bgr_planes1= new ArrayList<Mat>();
                    ArrayList<Mat> bgr_planes2= new ArrayList<Mat>();
                    Core.split(img1, bgr_planes1);
                    Core.split(img2, bgr_planes2);
                    MatOfFloat histRanges = new MatOfFloat (0f, 180f);
                    boolean accumulate = false;
                    Imgproc.calcHist(bgr_planes1, channels, new Mat(), hist1, histSize, histRanges, accumulate);
                    Core.normalize(hist1, hist1, 0, hist1.rows(), Core.NORM_MINMAX, -1, new Mat());
                    Imgproc.calcHist(bgr_planes2, channels, new Mat(), hist2, histSize, histRanges, accumulate);
                    Core.normalize(hist2, hist2, 0, hist2.rows(), Core.NORM_MINMAX, -1, new Mat());
                    img1.convertTo(img1, CvType.CV_32F);
                    img2.convertTo(img2, CvType.CV_32F);
                    hist1.convertTo(hist1, CvType.CV_32F);
                    hist2.convertTo(hist2, CvType.CV_32F);

                    double compare = Imgproc.compareHist(hist1, hist2, Imgproc.CV_COMP_CHISQR);

                    Toast.makeText(getApplicationContext(), ""+compare, Toast.LENGTH_LONG).show();

                    if(compare>0 && compare<1500) {
                        Toast.makeText(getApplicationContext(), "Türler Benzer Olabilir, İkinci Aşamaya Geçiliyor...", Toast.LENGTH_LONG).show();
                        //new asyncTask(MainActivity.this).execute();
                    }
                    else if(compare==0)
                        Toast.makeText(getApplicationContext(), "Türler Kesin Benzer !", Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(), "Türler Farklı Olabilir", Toast.LENGTH_LONG).show();


                     /*Mat descriptors, dupDescriptors;
                     FeatureDetector detector;
                     DescriptorExtractor DescExtractor;
                     DescriptorMatcher matcher;
                     MatOfKeyPoint keypoints, dupKeypoints;
                     MatOfDMatch matches, matches_final_mat;
                     boolean isDuplicate = false;
                     MainActivity asyncTaskContext=null;
                     Scalar RED = new Scalar(255,0,0);
                     Scalar GREEN = new Scalar(0,255,0);

                    Mat img3 = new Mat();
                    MatOfByte drawnMatches = new MatOfByte();
                    Features2d.drawMatches(img1, keypoints, img2, dupKeypoints,
                            matches_final_mat, img3, GREEN, RED, drawnMatches, Features2d.NOT_DRAW_SINGLE_POINTS);
                    bmp = Bitmap.createBitmap(img3.cols(), img3.rows(),
                            Bitmap.Config.ARGB_8888);
                    Imgproc.cvtColor(img3, img3, Imgproc.COLOR_BGR2RGB);
                    Utils.matToBitmap(img3, bmp);
                    List<DMatch> finalMatchesList = matches_final_mat.toList();
                    final int matchesFound=finalMatchesList.size();
                    if (finalMatchesList.size() > min_matches)
                    {
                        text = finalMatchesList.size()
                                + " matches were found. Possible duplicate image.\nTime taken="
                                + (endTime - startTime) + "ms";
                        isDuplicate = true;
                    } else {
                        text = finalMatchesList.size()
                                + " matches were found. Images aren't similar.\nTime taken="
                                + (endTime - startTime) + "ms";
                        isDuplicate = false;
                    }
                    pd.dismiss();
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            asyncTaskContext);
                    alertDialog.setTitle("Result");
                    alertDialog.setCancelable(false);
                    LayoutInflater factory = LayoutInflater.from(asyncTaskContext);
                    final View view = factory.inflate(R.layout.image_view, null);
                    ImageView matchedImages = (ImageView) view
                            .findViewById(R.id.finalImage);
                    matchedImages.setImageBitmap(bmp);
                    matchedImages.invalidate();
                    final CheckBox shouldBeDuplicate = (CheckBox) view
                            .findViewById(R.id.checkBox);
                    TextView message = (TextView) view.findViewById(R.id.message);
                    message.setText(text);
                    alertDialog.setView(view);
                    shouldBeDuplicate
                            .setText("These images are actually duplicates.");
                    alertDialog.setPositiveButton("Add to logs",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    File logs = new File(Environment
                                            .getExternalStorageDirectory()
                                            .getAbsolutePath()
                                            + "/imageComparator/Data Logs.txt");
                                    FileWriter fw;
                                    BufferedWriter bw;
                                    try {
                                        fw = new FileWriter(logs, true);
                                        bw = new BufferedWriter(fw);
                                        bw.write("Algorithm used: "
                                                + descriptorType
                                                + "\nHamming distance: "
                                                + min_dist + "\nMinimum good matches: "+min_matches
                                                +"\nMatches found: "+matchesFound+"\nTime elapsed: "+(endTime-startTime)+"seconds\n"+ path1
                                                + " was compared to " + path2
                                                + "\n" + "Is actual duplicate: "
                                                + shouldBeDuplicate.isChecked()
                                                + "\nRecognized as duplicate: "
                                                + isDuplicate + "\n");
                                        bw.close();
                                        Toast.makeText(
                                                asyncTaskContext,
                                                "Logs updated.\nLog location: "
                                                        + Environment
                                                        .getExternalStorageDirectory()
                                                        .getAbsolutePath()
                                                        + "/imageComparator/Data Logs.txt",
                                                Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        // TODO Auto-generated catch block
                                        // e.printStackTrace();
                                        try {
                                            File dir = new File(Environment
                                                    .getExternalStorageDirectory()
                                                    .getAbsolutePath()
                                                    + "/imageComparator/");
                                            dir.mkdirs();
                                            logs.createNewFile();
                                            logs = new File(
                                                    Environment
                                                            .getExternalStorageDirectory()
                                                            .getAbsolutePath()
                                                            + "/imageComparator/Data Logs.txt");
                                            fw = new FileWriter(logs, true);
                                            bw = new BufferedWriter(fw);
                                            bw.write("Algorithm used: "
                                                    + descriptorType
                                                    + "\nMinimum distance between keypoints: "
                                                    + min_dist + "\n" + path1
                                                    + " was compared to " + path2
                                                    + "\n"
                                                    + "Is actual duplicate: "
                                                    + shouldBeDuplicate.isChecked()
                                                    + "\nRecognized as duplicate: "
                                                    + isDuplicate + "\n");
                                            bw.close();
                                            Toast.makeText(
                                                    asyncTaskContext,
                                                    "Logs updated.\nLog location: "
                                                            + Environment
                                                            .getExternalStorageDirectory()
                                                            .getAbsolutePath()
                                                            + "/imageComparator/Data Logs.txt",
                                                    Toast.LENGTH_LONG).show();
                                        } catch (IOException e1) {
                                            // TODO Auto-generated catch block
                                            e1.printStackTrace();
                                        }*/

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && data != null)
        {

            try {

                Uri imageeURI = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageeURI);

                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                if(imgNO == 1)
                {
                    imgSelected1.setImageBitmap(selectedImage);
                }
                else
                {
                    imgSelected2.setImageBitmap(selectedImage);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }



        }

    }
}
