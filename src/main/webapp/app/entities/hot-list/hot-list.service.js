(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('HotList', HotList);

  HotList.$inject = ['$resource'];

  function HotList($resource) {
    var resourceUrl = 'api/hot-lists/:id';

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
