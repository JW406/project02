// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  googleRedirectUri: '/auth/google_redirect',
  googleAuthEndPoint:
    'https://accounts.google.com/o/oauth2/auth?response_type=code',
  googleScope:
    'scope=https://www.googleapis.com/auth/userinfo.email%20https://www.googleapis.com/auth/userinfo.profile',
  googleClientID:
    '348579708249-qobllrgbr5rr6vs4mjvgrlg331ld0ci0.apps.googleusercontent.com',
  githubAuthEndPoint: 'https://github.com/login/oauth/authorize?',
  githubRedirectUri: '/auth/github_redirect',
  githubScope: 'scope=user',
  githubClientID: '133547b73d6187c38434',
  stripeClientID:
    'pk_test_51JFjfADZSGMdHcapWzzZc7J0pzKeCKoGwSEJu32PXdKODASCpHtAjn8DknNMtqyCOrgbKflper35eyVZt7ZgTqLZ00sRkRDIp7',
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
