# PercentageBar

一个百分比条控件。

## 注

因项目时间紧，很多配置没写

## Import



## Usage

in xml:

    <com.fanhl.percentagebar.PercentageBar
        android:layout_width="match_parent"
        android:layout_height="30dp"
        android:layout_marginStart="16dp"
        android:layout_marginTop="16dp"
        android:layout_marginEnd="16dp"
        android:padding="4dp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:percentage=".43"/>

styleable:

    <declare-styleable name="PercentageBar">
        <attr name="foregroundDrawable" format="reference"/>
        <attr name="barHeight" format="dimension"/>
        <attr name="textSize" format="dimension"/>
        <attr name="textColor" format="color"/>
        <attr name="textPadding" format="dimension"/>
        <attr name="percentage" format="float"/>
    </declare-styleable>

## License

Copyright 2018 fanhl

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.