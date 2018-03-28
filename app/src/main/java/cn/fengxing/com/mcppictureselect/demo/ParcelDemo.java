package cn.fengxing.com.mcppictureselect.demo;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by fengxing on 2018/3/28.
 */

public class ParcelDemo implements Parcelable {

    private int count;
    private String name;
    private ArrayList<String> tags;
    private Book book;
    // ***** 注意: 这里如果是集合 ,一定要初始化 *****
    private ArrayList<Book> books = new ArrayList<>();


    /**
     * 序列化
     *
     * @param in
     */
    protected ParcelDemo(Parcel in) {
        count = in.readInt();
        name = in.readString();
        tags = in.createStringArrayList();

        // 读取对象需要提供一个类加载器去读取,因为写入的时候写入了类的相关信息
        book = in.readParcelable(Book.class.getClassLoader());


        //读取集合也分为两类,对应写入的两类

        //这一类需要用相应的类加载器去获取
        in.readList(books, Book.class.getClassLoader());// 对应writeList


        //这一类需要使用类的CREATOR去获取
        in.readTypedList(books, Book.CREATOR); //对应writeTypeList

        //books = in.createTypedArrayList(Book.CREATOR); //对应writeTypeList


        //这里获取类加载器主要有几种方式
        getClass().getClassLoader();
        Thread.currentThread().getContextClassLoader();
        Book.class.getClassLoader();


    }

    public static final Creator<ParcelDemo> CREATOR = new Creator<ParcelDemo>() {
        @Override
        public ParcelDemo createFromParcel(Parcel in) {
            return new ParcelDemo(in);
        }

        @Override
        public ParcelDemo[] newArray(int size) {
            return new ParcelDemo[size];
        }
    };

    /**
     * 描述
     *
     * @return
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * 反序列化
     *
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
        dest.writeString(name);
        //序列化一个String的集合
        dest.writeStringList(tags);
        // 序列化对象的时候传入要序列化的对象和一个flag,
        // 这里的flag几乎都是0,除非标识当前对象需要作为返回值返回,不能立即释放资源
        dest.writeParcelable(book, 0);

        // 序列化一个对象的集合有两种方式,以下两种方式都可以


        //这些方法们把类的信息和数据都写入Parcel，以使将来能使用合适的类装载器重新构造类的实例.所以效率不高
        dest.writeList(books);


        //这些方法不会写入类的信息，取而代之的是：读取时必须能知道数据属于哪个类并传入正确的Parcelable.Creator来创建对象
        // 而不是直接构造新对象。（更加高效的读写单个Parcelable对象的方法是：
        // 直接调用Parcelable.writeToParcel()和Parcelable.Creator.createFromParcel()）
        dest.writeTypedList(books);


    }
}
