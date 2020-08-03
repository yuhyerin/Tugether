// localhost url
// axios.defaults.baseURL = 'http://127.0.0.1:8080';
// aws url
// axios.defaults.baseURL = 'http://i3b303.p.ssafy.io';

module.exports = {
  "transpileDependencies": [
    "vuetify"
  ],
  devServer: {
    proxy: {
      '/api' : {
        target: 'http://127.0.0.1:8080',
        // changeOrigin: true,
        // pathRewrite: {
        //   '^/api' : ''
        // }
      }
    }
  }
}

// const api = axios.create({
//   baseURL: 'http://127.0.0.1:8080',
// });