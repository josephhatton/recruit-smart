(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('Book', Book);

  Book.$inject = ['$resource', 'DateUtils'];

  function Book($resource, DateUtils) {
    var resourceUrl = 'api/books/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true},
      'get': {
        method: 'GET',
        transformResponse: function (data) {
          if (data) {
            data = angular.fromJson(data);
            data.createdDate = DateUtils.convertDateTimeFromServer(data.createdDate);
          }
          return data;
        }
      },
      'update': {method: 'PUT'}
    });
  }
})();
