(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('SearchList', SearchList);

  SearchList.$inject = ['$resource'];

  function SearchList($resource) {
    var resourceUrl = 'api/search-lists/:id';

    return $resource(resourceUrl, {}, {
      'query': {method: 'GET', isArray: true},
      'get': {
        method: 'GET',
        transformResponse: function (data) {
          data = angular.fromJson(data);
          return data;
        }
      },
      'update': {method: 'PUT'}
    });
  }
})();
