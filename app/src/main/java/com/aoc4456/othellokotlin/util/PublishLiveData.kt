package com.aoc4456.othellokotlin.util

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * setValueしたときのみイベントを通知するLiveData
 *
 * 【参考】LiveDataをPublishSubjectっぽく使う
 *
 * https://star-zero.medium.com/livedata%E3%82%92publishsubject%E3%81%A3%E3%81%BD%E3%81%8F%E4%BD%BF%E3%81%86-5af83b33a433
 */
class PublishLiveData<T> : LiveData<T>() {

    private var internalLiveData = MutableLiveData<T>()

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        internalLiveData = MutableLiveData<T>()
        internalLiveData.observe(owner, observer)
    }

    public override fun setValue(value: T?) {
        super.setValue(value) // It need to increment internal version to use Transformations.map or Transformations.switchMap.
        internalLiveData.value = value!!
    }

    override fun getValue(): T? {
        return internalLiveData.value
    }

    override fun postValue(value: T?) {
        internalLiveData.postValue(value!!)
    }
}
