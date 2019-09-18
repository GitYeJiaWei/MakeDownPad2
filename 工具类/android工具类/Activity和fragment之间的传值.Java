一.Activity传值给Fragment

1.添加Fragment的Activity：

 Fragment01 fragment = new Fragment01();
        Bundle bundle = new Bundle();
        bundle.putString("str","这是Activity传来的值");
        fragment.setArguments(bundle);

        fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fm01id,fragment01);
        fragmentTransaction.commit();

2.Fragment中接受Activity传过来的值：

String str = (String)getArguments().get(“str”);




二。Fragment传值给Activity：

实现思路： 
1.在Fragment中写一个回调接口 
2.在activity中实现这个回调接口 
3.在Fragment中onAttach方法中得到activity中实现好的实例化接口对象 
4.用接口的对象进行传值

1.Activity：

public class MainActivity extends Activity implements CallBackValue{  

    private TextView tv1;  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  

        tv1 = (TextView) findViewById(R.id.tv1);  

        FragmentManager manager = getFragmentManager();  
        FragmentTransaction transaction =   manager.beginTransaction();  
        transaction.add(R.id.contents, new Fragmen1());  
        transaction.commit();  

    }  
    //要获取的值  就是这个参数的值  
    @Override  
    public void SendMessageValue(String strValue) {  
        // TODO Auto-generated method stub  
        tv1.setText(strValue);  
    }  
} 

2.Fragment：

public class Fragmen1 extends Fragment{  
    private Button btn1;  
    private EditText et1;  
    CallBackValue callBackValue;  

    /** 
     * fragment与activity产生关联是  回调这个方法  
     */  
    @Override  
    public void onAttach(Activity activity) {  
        // TODO Auto-generated method stub  
        super.onAttach(activity);  
        //当前fragment从activity重写了回调接口  得到接口的实例化对象  
        callBackValue =(CallBackValue) getActivity();  
    }  


    @Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        View view = inflater.inflate(R.layout.fragment_layout1, container, false);  
         btn1 = (Button) view.findViewById(R.id.btn1);  
         et1 = (EditText) view.findViewById(R.id.et1);  
         btn1.setOnClickListener(new OnClickListener() {  

            @Override  
            public void onClick(View v) {  
                // TODO Auto-generated method stub  
                String strValue = et1.getText().toString().trim();  
                callBackValue.SendMessageValue(strValue);  

            }  
        });  

        return view;  
    }  
    //定义一个回调接口  
    public interface CallBackValue{  
        public void SendMessageValue(String strValue);  
    }  
}  