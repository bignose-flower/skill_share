<template>
  <div>
    <!-- ボタンを追加 -->
    <button @click="fetchDataForHW">Fetch Data For HelloWorld</button>
    <button @click="fetchDataForDB">Fetch Data For DB</button>

    <!-- 取得したデータを表示 -->
    <div v-if="responseData">
      <p>Response from http://localhost:8080/:</p>
      <pre>{{ responseData }}</pre>
    </div>
  </div>
</template>

<script setup>
// importなどの必要なコードを追加
import { ref } from 'vue'

// ボタンクリックでデータを取得するメソッド
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

const fetchDataForDB = async () => {
    try {
        const response = await fetch('http://localhost:8081/testdb');
        const data = await response.text();
        // 取得したデータをresponseDataに保存
        responseData.value = data;
    } catch (error) {
        console.error('Error fetching data:', error);
    }
}

// データを保存する変数
const responseData = ref(null);
</script>