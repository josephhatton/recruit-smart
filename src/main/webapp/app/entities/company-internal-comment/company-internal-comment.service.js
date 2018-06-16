(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('CompanyInternalComment', CompanyInternalComment);

  CompanyInternalComment.$inject = ['$resource'];

  function CompanyInternalComment($resource) {
    var resourceUrl = 'api/company-internal-comments/:id';

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
