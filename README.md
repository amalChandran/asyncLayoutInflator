## Problem

  Every app developer might have faced this issue. You get the design and mock up from the designer. You start the UI and implement all the animation your desginers need. Now when you start writing your business logic and integrate all your moving parts, you come across drops in your animations. You have no clue whats wrong. 
  
  You break your head and run all kinds of profiling tools on your code during tight deadlines to ship your product. All this boils down to adding more load to your UI thread, when your UI thread is loaded, the choreographer drops frames. This is ripps ur animation off completely.
  
## Solution:

  When iOS and Andorid platforms were developed, the mobile devices at that time had limited computing power. So the UI rendering is only done on UI thread at one go. With the current generation phones, having multi cores, its not effectively used. 
  
   To maintain the 60fps on your phones android released [Async layout inflator](https://developer.android.com/reference/android/support/v4/view/AsyncLayoutInflater.html). This is intended for parts of the UI that are created lazily or in response to user interactions. This allows the UI thread to continue to be responsive & animate while the relatively heavy inflate is being performed.
   
## Demo 
 
   Check out the demo app which has a loader. Try inflating views with and without asynchronous layout to see the difference for yourself. 
   
   
   ```markdown
         LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         View view = inflater.inflate(R.layout.layout_dummy, null);
         LinearLayout inflatedLayout = (LinearLayout) view.findViewById(R.id.layout_dummy);
         parentLayout.addView(inflatedLayout);
   ```
   
  When you load normally, there will be flicker on the loader. With async, this flicker is reduced to great extent.
  [Demo video of layoutinflation wint async](https://youtu.be/ZETNOieGZLA)
  
# Sample data:
Inflating 100 layouts took 1004 milli seconds without async inflation and 557 milli seconds with it.

Do try the poc to feel the difference.
 
    
## iOS
 
  For iOS take a look at https://github.com/facebook/AsyncDisplayKit .
