module.exports = {
  devServer: {
    proxy: [
      {
        context: ['/api/**', '/auth/**', '/pay/**', '/pay-cb/**'],
        target: 'http://localhost:8080',
      },
    ],
  },
}
