package socket.socket_005.sample_08;

import java.util.List;  

public class Test {  
  
    private static int port = 10000;  
  
    public static void main(String[] args) throws Exception {  
        //test1();  
        test2();  
    }  
  
    public static void test1() throws Exception {  
        MyServer myServer = new MyServerSimpleImpl(port);  
        myServer.startup();  
        Thread.sleep(3000);  
  
        MyClient myClient = new MyClientSimpleImpl("localhost", port);  
  
        MyRequest request = null;  
        MyResponse response = null;  
  
        request = new MyGenericRequest(MyUserServiceImpl.class, "list", new Class<?>[]{int.class}, new Object[]{2});  
        response = myClient.execute(request);  
        System.out.println(response.getResult());  
        List<User> users = myClient.execute(request, new MyGenericResponseHandler<List<User>>());  
        System.out.println(users);  
  
        request = new MyGenericRequest(MyUserServiceImpl.class, "findByName", new Class<?>[]{String.class}, new Object[]{"kongxx"});  
        response = myClient.execute(request);  
        System.out.println(response.getResult());  
        User user = myClient.execute(request, new MyGenericResponseHandler<User>());  
        System.out.println(user);  
  
        response = myClient.execute(new MyGenericRequest(MyUserServiceImpl.class, "test", new Class<?>[]{}, new Object[]{}));  
        System.out.println(response.getResult());  
    }  
  
    public static void test2() throws Exception {  
        MyServer myServer = new MyServerNIOImpl(port);  
        myServer.startup();  
        Thread.sleep(3000);  
  
        MyClient myClient = new MyClientNIOImpl("localhost", port);  
  
        MyRequest request = null;  
        MyResponse response = null;  
  
        request = new MyGenericRequest(MyUserServiceImpl.class, "list", new Class<?>[]{int.class}, new Object[]{2});  
        response = myClient.execute(request);  
        System.out.println(response.getResult());  
        List<User> users = myClient.execute(request, new MyGenericResponseHandler<List<User>>());  
        System.out.println(users);  
  
        request = new MyGenericRequest(MyUserServiceImpl.class, "findByName", new Class<?>[]{String.class}, new Object[]{"kongxx"});  
        response = myClient.execute(request);  
        System.out.println(response.getResult());  
        User user = myClient.execute(request, new MyGenericResponseHandler<User>());  
        System.out.println(user);  
  
        response = myClient.execute(new MyGenericRequest(MyUserServiceImpl.class, "test", new Class<?>[]{}, new Object[]{}));  
        System.out.println(response.getResult());  
    }  
}  
