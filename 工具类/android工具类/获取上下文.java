ToastUtils.toast(getContext(),object.optString("Message"));
//getContext() 获取上下文，如果是在当前activity可以用StopCw.this 就是activity.this,
//如果不是就用getApplication()，是从所有应用的activity中获取当前的上下文