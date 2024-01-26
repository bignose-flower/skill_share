<template>
  <div>
    <!-- ボタンを追加 -->
    <!-- @clickが<script>タグ内のfunctionに紐づいている -->
    <dev>
      <button @click="fetchDataForHW">Fetch Data For HelloWorld</button>
      <button @click="fetchDataForDB">Fetch Data For DB</button>
    </dev>
    <select v-model="selectedNumber">
      <option value="1">1</option>
      <option value="2">2</option>
      <option value="3">3</option>
      <!-- 追加の数字をここに続けて記述 -->
      <option value="4">4</option>
    </select>

    <!-- 取得したデータを表示 -->
    <div v-if="responseData">
      <p>Response from http://localhost:8080/:</p>
      <pre>{{ responseData }}</pre>
    </div>
    
    <br>
    <dev>
      <button @click="fetchAllDataForDB">Fetch All Data For DB</button>
    </dev>
    <div v-if="dataList">
      <p>Response from http://localhost:8080/:</p>
      <ul>
        <li v-for="(item,index) in dataList" :key="index" >{{ item }}</li>
      </ul>
    </div>
  </div>
</template>

<script setup>
// importなどの必要なコードを追加
import { ref } from 'vue'

// "Fetch Data For HelloWorld"ボタン押下時の処理
const fetchDataForHW = async () => {
    try {
        const response = await fetch('http://localhost:8081/hello');
        const data = await response.text();
        // 取得したデータをresponseDataに保存
        responseData.value = data;
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// "Fetch Data For DB"ボタン押下時の処理
const fetchDataForDB = async () => {
    try {
        const selectedValue = selectedNumber.value;

        const response = await fetch(`http://localhost:8081/testdb/${selectedValue}`);
        const data = await response.text();
        // 取得したデータをresponseDataに保存
        responseData.value = data;
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// "Fetch All Data For DB"ボタン押下時の処理
const fetchAllDataForDB = async() => {
    try{
      const response = await fetch('http://localhost:8081/testdb');
      const data = await response.json()
      dataList.value = data;
    } catch (error) { 
      console.error('Error fetching data:', error);
    }
}

// データを保存する変数
const responseData = ref(null);
const selectedNumber = ref("1");
const dataList = ref(null);
</script>