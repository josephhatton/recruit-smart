(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('HiringContact', HiringContact);

  HiringContact.$inject = ['$resource'];

  function HiringContact($resource) {
    var resourceUrl = 'api/hiring-contacts/:id';

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
