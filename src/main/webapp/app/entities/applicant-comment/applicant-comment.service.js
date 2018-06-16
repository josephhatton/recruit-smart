(function () {
  'use strict';
  angular
    .module('recruitsmartApp')
    .factory('ApplicantComment', ApplicantComment);

  ApplicantComment.$inject = ['$resource'];

  function ApplicantComment($resource) {
    var resourceUrl = 'api/applicant-comments/:id';

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
