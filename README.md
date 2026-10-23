# 校园巡检 App (实验二 & 实验三)

## 实验二：网络安全对比实验
### 实验目标
掌握 Android 网络权限配置，理解 `network_security_config` 网络安全配置，对比 HTTP 与 HTTPS 请求的访问差异。

### 运行与测试方法
1. 在 Android 模拟器/真机打开 App。
2. 点击【HTTP 请求】访问 `http://httpbin.org/get?room=A203`。
3. 点击【HTTPS 请求】访问 `https://httpbin.org/get?room=A204`。
4. 在界面查看请求响应结果，观察明文配置拦截情况。

### 接口地址
- HTTP 地址：`http://httpbin.org/get?room=A203`
- HTTPS 地址：`https://httpbin.org/get?room=A204`
> **注意**：HTTP 仅用于教学对比，实际服务优先使用 HTTPS。


---

## 实验三：工程拆分与 OkHttp 接入
### 实验目标
将原有的单文件网络测试页，重构并整理为职责明确的“校园巡检页面”，并接入第三方网络库 OkHttp 完成实际的 HTTPS 请求。

### 文件职责拆分
- `MainActivity.kt`：系统入口。负责设置 Compose 内容，安排异步请求与更新 UI 状态。
- `InspectionScreen.kt`：UI 页面。负责显示巡检数据、按钮交互，以及计算“未检查数量”。
- `InspectionNetwork.kt`：网络层。负责创建 `OkHttpClient`，执行网络请求并处理响应和异常。

### 运行与验证方法
1. 运行 App，确认页面显示：巡检员（李佳润）、实验室（A203）、设备总数（10）、已检查（3）、未检查（7）。
2. 点击【请求 A203】，观察下方输出是否返回 HTTP 200 及 `"room": "A203"`。
3. 点击【请求 A204】，观察下方输出是否返回 HTTP 200 及 `"room": "A204"`（此时页面固定的实验室仍为 A203）。
