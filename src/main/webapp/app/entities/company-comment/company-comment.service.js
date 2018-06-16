(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('CompanyComment', CompanyComment);

  CompanyComment.$inject = ['$resource'];

  function CompanyComment($resource) {
    var resourceUrl = 'api/company-comments/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true},
      'get': {
        method: 'GET',
        transformResponse: function (data) {
          if (data) {
            data = angular.fromJson(data);
          }
          return data;
        }
      },
      'update': {method: 'PUT'}
    });
  }
})();
