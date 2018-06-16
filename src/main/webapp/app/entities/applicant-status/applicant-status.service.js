(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('ApplicantStatus', ApplicantStatus);

  ApplicantStatus.$inject = ['$resource'];

  function ApplicantStatus($resource) {
    var resourceUrl = 'api/applicant-statuses/:id';

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
