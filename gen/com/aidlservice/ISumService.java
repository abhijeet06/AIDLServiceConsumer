/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Abhijeet Kumar\\workspace\\AIDLServiceConsumer\\src\\com\\aidlservice\\ISumService.aidl
 */
package com.aidlservice;
public interface ISumService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.aidlservice.ISumService
{
private static final java.lang.String DESCRIPTOR = "com.aidlservice.ISumService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.aidlservice.ISumService interface,
 * generating a proxy if needed.
 */
public static com.aidlservice.ISumService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.aidlservice.ISumService))) {
return ((com.aidlservice.ISumService)iin);
}
return new com.aidlservice.ISumService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_fibonacciSum:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
long _result = this.fibonacciSum(_arg0);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.aidlservice.ISumService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public long fibonacciSum(long n) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(n);
mRemote.transact(Stub.TRANSACTION_fibonacciSum, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_fibonacciSum = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public long fibonacciSum(long n) throws android.os.RemoteException;
}
