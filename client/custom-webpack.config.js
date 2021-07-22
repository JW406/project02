module.exports = {
  devServer: {
    proxy: [
      {
        context: ['/api/**', '/auth/**', '/pay/**'],
        target: 'http://localhost:8080',
      },
    ],
  },
}
