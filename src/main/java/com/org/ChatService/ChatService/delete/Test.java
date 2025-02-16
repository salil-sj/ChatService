package com.org.ChatService.ChatService.delete;

@FunctionalInterface
interface interf
{
    public void sum(int a , int b);
}


public class Test
{

    public  void sumMethodinStatic(int a , int b)
    {
        System.out.println("Resul from static is " + (a+b));
    }

    public static void main(String[] args) {

        Test t = new Test();

        interf i = t::sumMethodinStatic;
        i.sum(4,5);

    }

}
