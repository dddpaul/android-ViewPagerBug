### DESCRIPTION

This project demonstrates some weird Android fragment allocation behaviour.

This application has two fragments and two layouts:
* portrait layout with ViewPager as root view; 
* landscape layout without Viewpager (in this example LinearLayout is used as root view).
 
The reason for this setup - I can display all fragments simultaneously while in landscape mode.

And this works fine until we don't look inside heap more closely. So I did it because I've observed strange behaviour in my [SimpleNetCat application](https://github.com/dddpaul/android-SimpleNetCat) when trying to implement multi-pane layout pattern. 

Testing setup:
* OS - Ubuntu 12.04 LTS X86_64;
* AVD - Android 4.1.2 (API 16), Intel Atom (x86); 
* DDMS and VisualVM.

So we begin ...

### Application is launched in portrait mode.  

**1.** Start our application in portrait mode (with ViewPager), get a heapdump (viewpagerbug.hprof in heapdumps.7z).

![Expected behaviour, 2 fragments are allocated](/screenshots/viewpagerbug.png?raw=true)

Expected behaviour, 2 fragments are allocated.

**2.** Change orientation to landscape, get a heapdump (viewpagerbug_oc.hprof, "oc" suffix is for "orientation change"). 

![Holy shit, 6 fragments are allocated!](/screenshots/viewpagerbug_oc.png?raw=true)

Holy shit, 6 fragments are allocated! Why?!

**3.** Run GC, get a heapdump (viewpagerbug_oc_gc.hprof, "gc" suffix is for "garbage collector"). 

![2 fragments from portrait activity was collected](/screenshots/viewpagerbug_oc_gc.png?raw=true)

2 fragments from old portrait activity was collected. But we still have 4 fragments instead of 2! And all of them in RESUMED state.
 
### Application is launched in landscape mode.  

**1.** Start our application in landscape mode (without ViewPager), get a heapdump (viewpagerbug_landscape.hprof).

![Expected behaviour, 2 fragments are allocated](/screenshots/viewpagerbug_landscape.png?raw=true)

Expected behaviour, 2 fragments are allocated.
 
**2.** Change orientation to portrait, get a heapdump (viewpagerbug_landscape_oc.hprof). 

![Again, 6 fragments are allocated](/screenshots/viewpagerbug_landscape_oc.png?raw=true)

Again, 6 fragments are allocated.

**3.** Run GC, get a heapdump (viewpagerbug_landscape_oc_gc.hprof). 

![2 fragments from landscape activity was collected](/screenshots/viewpagerbug_oc_gc.png?raw=true)

2 fragments from old landscape activity was collected. Still we have 4 resumed fragments instead of 2.

### Application is launched WITHOUT ViewPager at all.  

**1.** Checkout "without_viewpager" branch first and then start application. Orientation doesn't matter in this case, but I've used portrait.
 
![Expected behaviour, 2 fragments are allocated](/screenshots/viewpagerbug_without-viewpager.png?raw=true)

Expected behaviour, 2 fragments are allocated.

**2.** Change orientation to landscape, get a heapdump (viewpagerbug_without-viewpager_oc.hprof). 

![Expected behaviour, 4 fragments are allocated](/screenshots/viewpagerbug_without-viewpager_oc.png?raw=true)

Expected behaviour, 4 fragments are allocated.
 
**3.** Run GC, get a heapdump (viewpagerbug_without-viewpager_oc_gc.hprof). 

![2 fragments from portrait activity was collected](/screenshots/viewpagerbug_without-viewpager_oc_gc.png?raw=true)

2 fragments from old portrait activity was collected. We have 2 resumed fragments as expected. So, without ViewPager it's all going smooth and clear and that's the reason of this application name :)
