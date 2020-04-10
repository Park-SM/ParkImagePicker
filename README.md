# ParkImagePicker
Android simple image picker library.

# How to use
Add to build.gradle
<pre>
implementation 'com.google.android.material:material:1.1.0'
implementation 'com.github.bumptech.glide:glide:4.11.0'
implementation 'com.smparkworld.parkimagepicker:parkimagepicker:1.0.0'
</pre>


# Basic example
<pre>
new ParkImagePicker(context).show();
</pre>


# Options
<pre>
new ParkImagePicker(context)
    .setOnSelectedListener(new ParkImagePicker.OnImageSelectedListener() {
        @Override
        public void onImageSelected(String uri) {

        }
    })
    .setOnSelectedImageView(imageView)    // set image to ImageView.
    .setNumOfColumns(4)                   // default value is 3.
    .setTakePictureBtn(true)              // default value is true.
    .setTaskPictureBtnIcon(R.drawable.?)
    .setTitle("Sample title")
    .setTitleFontColor(R.color.?)
    .setTitleBackgroundColor(R.color.?)
    .show();
</pre>


# License
<pre>
Copyright 2020 ParkSM

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.```
</pre>
