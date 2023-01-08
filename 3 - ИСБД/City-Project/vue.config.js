module.exports = {
  publicPath: process.env.NODE_ENV === 'production'
      ? 'https://se.ifmo.ru/~s285583/CourseWork'
      : '/',
  transpileDependencies: [
    'vuetify'
  ]
}
